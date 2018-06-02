import React, { Component } from 'react';
import _ from 'lodash';

import Graph from './components/graph/Graph';

import './App.css';

const API_URL = "https://blockchain-explorer-was-taken.herokuapp.com";
const BLOCKCHAIN_TYPES = { BITCOIN: 'bitcoin', ETHEREUM: 'ethereum' };
const BLOCKCHAIN_TYPES_VALUES = Object.values(BLOCKCHAIN_TYPES);

class App extends Component {
    constructor(props) {
        super(props);
        this.state = { type: BLOCKCHAIN_TYPES_VALUES[0] };
    }

    setType = ({ target: { value } }) => this.setState({ type: value });

    renderOption = (type) => <option value={type} key={type}>{_.capitalize(type)}</option>;

    setAddress = ({ target: { value } }) => this.setState({ address: value });

    fetchData = () => {
        const { type, address } = this.state;

        fetch(`${API_URL}/${type}/transactions/${address}`)
            .then(response => console.log(response))
    }

    render() {
        return (
            <div>
                <h1>Blockchain Explorer</h1>
                <select onChange={this.setType} value={this.state.type}>
                    {BLOCKCHAIN_TYPES_VALUES.map(this.renderOption)}
                </select>
                <input onChange={this.setAddress} />
                <button onClick={this.fetchData}>Explore</button>
                <Graph />
            </div>
        );
    }
}

export default App;
