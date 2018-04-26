import React, { PureComponent } from 'react';
import { select } from 'd3-selection';
import { forceSimulation, forceManyBody, forceLink, forceX, forceY } from 'd3-force';
import './Graph.css';

class Graph extends PureComponent {
    renderGraph = () => {
        // To be move to component's method
        function ticked() {
            selectedLinks.attr('x1', (d) => d.source.x)
                .attr('y1', (d) => d.source.y)
                .attr('x2', (d) => d.target.x)
                .attr('y2', (d) => d.target.y);

            selectedNodes.attr("transform", (d) => `translate(${d.x},${d.y})`);
        }

        const width = 640;
        const height = 480;
        const r = 20;
        const fontSize = 16;
        const textOffset = fontSize / 4;

        const nodes = [
            { address: 'sampleNode1' },
            { address: 'sampleNode2' },
            { address: 'sampleNode3' },
            { address: 'sampleNode4' },
        ];

        const links = [
            { source: 'sampleNode1', target: 'sampleNode2' },
            { source: 'sampleNode1', target: 'sampleNode3' },
            { source: 'sampleNode1', target: 'sampleNode4' },
        ]

        const graph = select('.graph')
            .append('svg')
            .attr('width', width)
            .attr('height', height);

        const simulation = forceSimulation()
            .force("charge", forceManyBody().strength(-300))
            .force('link', forceLink().id((d) => d.address).distance(150))
            .force("x", forceX(width / 2))
            .force("y", forceY(height / 2))
            .on('tick', ticked);

        let selectedLinks = graph.selectAll('.link');
        let selectedNodes = graph.selectAll('.node');

        simulation.nodes(nodes);
        simulation.force('link').links(links);

        selectedLinks = selectedLinks.data(links)
            .enter().append('line')
            .attr('class', 'link');

        selectedNodes = selectedNodes.data(nodes)
            .enter().append('g')
            .attr('class', 'node');

        selectedNodes.append('circle')
            .attr('r', r);

        selectedNodes.append('text')
            .attr("dx", (d) => r + textOffset)
            .attr("dy", (d) => textOffset)
            .text((d) => d.address);
    }

    componentDidMount() {
        this.renderGraph();
    }

    render() {
        return <div className="graph" />;
    }
}

export default Graph;
