import React, { Component } from 'react';
import _ from 'lodash';
import { select } from 'd3-selection';
import { forceSimulation, forceManyBody, forceLink } from 'd3-force';

import './Graph.css';

const BASE_RADIUS = 10;
const ARROW_SIZE = 5;
const CHARGE_STRENGTH = -30;
const SHORT_ADDRESS_LENGTH = 6;
const VALUE_DECIMAL_PLACES = 4;
const EDGE_OFFSET = 50;
const LABEL = { WIDTH: 60, OFFSET: 20};
const TOOLTIP = { WIDTH: 530, HEIGHT: 200};
const GRAPH_TYPES = { INCOMING: 'incoming', OUTGOING: 'outgoing'};

class Graph extends Component {
    reduceDuplications = (prev, curr) => {
        const duplicated = prev.find(({address}) => address === curr.address);

        return duplicated ? prev : prev.concat(curr);
    };

    getTicked = (links, nodes) => (() => {
        links.attr('d', ({ target, source }) => {
            const dx = target.x - source.x;
            const dy = target.y - source.y;
            const dr = Math.sqrt(dx * dx + dy * dy);

            return `M${source.x},${source.y}A${dr},${dr} 0 0,1 ${target.x},${target.y}`;
        });

        nodes.attr('transform', (d) => `translate(${d.x},${d.y})`);
    });

    getSize = (val) => Math.log(val + 1) * BASE_RADIUS;

    getDirection = (type) => type === GRAPH_TYPES.INCOMING ? 'from' : 'to';

    getShortAddress = (address) => address.substring(0, SHORT_ADDRESS_LENGTH).concat('...');

    showTooltip = ({ x, y, size, address, transactions }, width, height) => {
        const horizontalPosition = x + size + TOOLTIP.WIDTH < width ? x + size : x - TOOLTIP.WIDTH - 2 * size;
        const verticalPosition = y + size + TOOLTIP.HEIGHT < height ? y + size : y - TOOLTIP.HEIGHT - 2 * size;

        this.hideTooltip();

        const tooltip = select('.graph')
            .insert('div')
            .attr('class', 'tooltip')
            .style('left', `${horizontalPosition}px`)
            .style('top', `${verticalPosition}px`);
        
        tooltip.append('h3')
            .attr('class', 'title')
            .text(address);

        const wrapper = tooltip
            .append('div')
            .attr('class', 'transactions__wrapper');

        wrapper
            .append('div')
            .attr('class', 'transactions--incoming')
            .append('h4')
            .text(_.capitalize(GRAPH_TYPES.INCOMING));
        wrapper
            .append('div')
            .attr('class', 'transactions--outgoing')
            .append('h4')
            .text(_.capitalize(GRAPH_TYPES.OUTGOING));

        transactions.forEach(({ value, type, address }) => {
            const container = tooltip
                .select('.transactions__wrapper')
                .select(`.transactions--${type}`);

            container
                .append('p')
                .text(`${value} Ether   ${this.getDirection(type)}   ${this.getShortAddress(address)}`);
        });
    };

    hideTooltip = () => select('.graph').select('.tooltip').remove();

    renderGraph = () => {
        const { address, inTransactions, outTransactions, onClick } = this.props;
        const svg = select('.graph').append('svg').on('click', () => this.hideTooltip());
        const { width, height } = svg.node().getBoundingClientRect();
        const offsetWidth = width > 2 * height ? (width - 2 * height) / 2 : 0;
        const adjustedWidth = width > 2 * height ? 2 * height : width;
        const simulation = forceSimulation()
            .force('charge', forceManyBody().strength(CHARGE_STRENGTH))
            .force('link', forceLink().id((d) => d.address).distance(height / 2 - EDGE_OFFSET));

        svg.append("defs")
            .selectAll("marker")
            .data(["arrow"])
            .enter()
            .append("marker")
            .attr("id", String)
            .attr("viewBox", "0 -5 10 10")
            .attr("markerWidth", ARROW_SIZE)
            .attr("markerHeight", ARROW_SIZE)
            .attr("orient", "auto")
            .attr('class', 'arrow')
            .append("path")
            .attr("d", "M0,-5L10,0L0,5");

        const inLinks = inTransactions.map((transaction) =>
            ({ source: transaction.address, target: address, type: 'outgoing', value: transaction.value }));
        const outLinks = outTransactions.map((transaction) =>
            ({ source: address, target: transaction.address, type: 'incoming', value: transaction.value }));
        const links = [...inLinks, ...outLinks];

        const selectedLinks = svg
            .selectAll('.link')
            .data(links)
            .enter()
            .append('path')
            .attr('class', (d) => `link ${d.type}`)
            .attr('marker-mid', 'url(#arrow)');

        const fromNodes = inTransactions.map((node) =>
            ({ ...node, x: adjustedWidth / 4 + offsetWidth, y: height / 2 }));
        const centerNode = { address, x: adjustedWidth / 2 + offsetWidth, y: height / 2 };
        const toNodes = outTransactions.map((node) =>
            ({ ...node, x: adjustedWidth / 4 * 3 + offsetWidth, y: height / 2 }));
        const nodes = [ ...fromNodes, centerNode, ...toNodes].reduce(this.reduceDuplications, []);

        links.forEach((link) => {
            const { value, source: sourceAddress, target: targetAddress } = link;
            const source = nodes.find((node) => node.address === sourceAddress);
            const sourceTransaction =
                { value: value.toFixed(VALUE_DECIMAL_PLACES), type: 'incoming', address: targetAddress };
            const target = nodes.find((node) => node.address === targetAddress);
            const targetTransaction =
                { value: value.toFixed(VALUE_DECIMAL_PLACES), type: 'outgoing', address: sourceAddress };

            source.transactions = source.transactions ?
                source.transactions.concat(sourceTransaction) : [].concat(sourceTransaction);
            target.transactions = target.transactions ?
                target.transactions.concat(targetTransaction) : [].concat(targetTransaction);
        });

        nodes.map((node) => {
            node.size = this.getSize(node.transactions.length);

            return node;
        });

        const selectedNodes = svg
            .selectAll('.node')
            .data(nodes)
            .enter()
            .append('g')
            .on('click', (d) => onClick(d.address))
            .on('mouseover', (d) => this.showTooltip(d, width, height))
            .attr('class', 'node');

        selectedNodes.append('circle').attr('r', (d) => d.size);
        selectedNodes.append('text')
            .attr('dx', () => -LABEL.WIDTH / 2)
            .attr('dy', (d) => LABEL.OFFSET + d.size)
            .text((d) => this.getShortAddress(d.address));

        simulation.nodes(nodes);
        simulation.force('link').links(links);
        simulation.on('tick', this.getTicked(selectedLinks, selectedNodes));
    };

    componentDidMount() {
        this.renderGraph();
    }

    componentWillUpdate() {
        const graph = select('.graph');
        graph.selectAll('*').remove();
    }

    componentDidUpdate() {
        this.renderGraph();
    }

    render() {
        return <div className="graph" />;
    }
}

export default Graph;
