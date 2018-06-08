import React, { Component } from 'react';
import _ from 'lodash';
import { select } from 'd3-selection';
import { forceSimulation, forceManyBody, forceLink } from 'd3-force';

import './Graph.css';

const R = 5;
const CHARGE_STRENGTH = -30;

class Graph extends Component {
    reduceDuplications = (prev, curr) => {
        const duplicated = prev.find(({address}) => address === curr.address);

        if (duplicated) {
            duplicated.transactions += 1;
            return prev;
        }

        return prev.concat(curr);
    };

    getTicked = (links, nodes, width, height) => () => {
        links.attr('d', ({ target, source }) => {
            const dx = target.x - source.x;
            const dy = target.y - source.y;
            const dr = Math.sqrt(dx * dx + dy * dy);

            return `M${source.x},${source.y}A${dr},${dr} 0 0,1 ${target.x},${target.y}`;
        });

        nodes.attr('transform', (d) => `translate(${d.x},${d.y})`);
    };

    renderGraph = () => {
        const { address, inTransactions, outTransactions } = this.props;
        const graph = select('.graph');
        const { width, height } = graph
            .node()
            .getBoundingClientRect();
        const offsetWidth = width > 2 * height ? (width - 2 * height) / 2 : 0;
        const adjustedWidth = width > 2 * height ? 2 * height : width;
        const simulation = forceSimulation()
            .force('charge', forceManyBody().strength(CHARGE_STRENGTH))
            .force('link', forceLink().id((d) => d.address).distance(height / 2 - 30));

        graph.append("defs")
            .selectAll("marker")
            .data(["arrow"])
            .enter()
            .append("marker")
            .attr("id", String)
            .attr("viewBox", "0 -5 10 10")
            .attr("markerWidth", R)
            .attr("markerHeight", R)
            .attr("orient", "auto")
            .attr('class', 'arrow')
            .append("path")
            .attr("d", "M0,-5L10,0L0,5");

        const inLinks = inTransactions.map((transaction) => ({ source: transaction.address, target: address, class: 'incoming' }));
        const outLinks = outTransactions.map((transaction) => ({ source: address, target: transaction.address, class: 'outgoing' }));
        const links = [...inLinks, ...outLinks];

        const selectedLinks = graph
            .selectAll('.link')
            .data(links)
            .enter().append('path')
            .attr('class', (d) => `link ${d.class}`)
            .attr('marker-mid', 'url(#arrow)');

        const fromNodes = inTransactions.map((node) => ({ ...node, x: adjustedWidth / 4 + offsetWidth, y: height / 2, transactions: 1 }));
        const centerNode = { address, x: adjustedWidth / 2 + offsetWidth, y: height / 2, transactions: links.length };
        const toNodes = outTransactions.map((node) => ({ ...node, x: adjustedWidth / 4 * 3 + offsetWidth, y: height / 2, transactions: 1 }));
        const nodes = [ ...fromNodes, centerNode, ...toNodes].reduce(this.reduceDuplications, []);
        const selectedNodes = graph
            .selectAll('.node')
            .data(nodes)
            .enter()
            .append('g')
            .attr('class', 'node');

        selectedNodes.append('circle')
            .attr('r', (d) => Math.log(d.transactions + 1) * R);

        selectedNodes.append('text')
            .attr('dx', () => -30)
            .attr('dy', (d) => 20 + Math.log(d.transactions + 1) * R)
            .text((d) => d.address.substring(0, 6).concat('...'));

        simulation.nodes(nodes);
        simulation.force('link')
            .links(links);
        simulation.on('tick', this.getTicked(selectedLinks, selectedNodes, width, height));
    }

    shouldComponentUpdate({ address, inTransactions, outTransactions }) {
        return !_.isUndefined(inTransactions) &&
            !_.isUndefined(outTransactions) &&
            address === this.props.address;
    }

    componentWillUpdate() {
        const graph = select('.graph');
        graph.selectAll('*').remove();
    }

    componentDidUpdate() {
        this.renderGraph();
    }

    render() {
        return <svg className="graph" />;
    }
}

export default Graph;
