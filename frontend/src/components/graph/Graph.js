import React, { PureComponent } from 'react';
import { select } from 'd3-selection';
import { forceSimulation } from 'd3-force';

class Graph extends PureComponent {
    renderGraph = () => {
        const width = 640;
        const height = 480;

        const nodes = [
            { x: width / 2, y: height / 2 },
        ];

        const graph = select('.graph')
            .append('svg')
            .attr('width', width)
            .attr('height', height);

        forceSimulation(nodes);

        graph.selectAll('.node')
            .data(nodes)
            .enter().append('circle')
            .attr('class', 'node')
            .attr('r', width / 25)
            .attr('cx', (d) => d.x)
            .attr('cy', (d) => d.y);
    }

    componentDidMount() {
        this.renderGraph();
    }

    render() {
        return <div className="graph" />;
    }
}

export default Graph;
