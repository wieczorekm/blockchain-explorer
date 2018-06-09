import React, { Component } from 'react';
import _ from 'lodash';

import Graph from './components/graph/Graph';

import './App.css';
import link from './assets/link.svg';
import fan from './assets/fan.svg';

const API_URL = "https://blockchain-explorer-was-taken.herokuapp.com";
const BLOCKCHAIN_TYPE = 'ethereum';
const BLOCK_NUMBER = { MIN: 0, MAX: 99999999 };

class App extends Component {
    constructor(props) {
        super(props);
        this.state = { startBlock: BLOCK_NUMBER.MIN, endBlock: BLOCK_NUMBER.MAX };
    }

    renderOption = (type) => <option value={type} key={type}>{_.capitalize(type)}</option>;

    fetchFormData = (event) => {
        const { target: { address, startBlock, endBlock } } = event;

        event.preventDefault();
        this.setState({ spinner: true });
        this.fetchData(address.value, startBlock.value, endBlock.value);
    };

    fetchNodeData = (address) => {
        const { startBlock, endBlock } = this.state;

        this.setState({ spinner: true });
        this.fetchData(address, startBlock, endBlock);
    };

    fetchData = (address, startBlock, endBlock) => {
        fetch(`${API_URL}/${BLOCKCHAIN_TYPE}/transactions/${address}?${startBlock}&${endBlock}`)
            .then((response) => {
                if (!response.ok) {
                    throw response;
                }

                return response.json();
            })
            .then(response => this.setState({ ...response, error: null, spinner: false }))
            .catch((error) =>
                error
                    .json()
                    .then(({ message }) => this.setState({ error: message, spinner: false }))
            );
    };

    render() {
        const { error, spinner, minedBlocksReward, address, ...graphParams } = this.state;

        return (
            <div className="app">
                <div className="logo">
                    <img src={link} />
                    <h1>
                        Blockchain Explorer
                    </h1>
                </div>
                <form className="toolbar" onSubmit={this.fetchFormData}>
                    <h2>
                        Explore
                        <span className="bold">{BLOCKCHAIN_TYPE}</span>
                        blockchain
                    </h2>
                    <div className="toolbar__entry">
                        <label>Address: </label>
                        <input name="address" required />
                    </div>
                    <div className="toolbar__entry">
                        <label>Start block: </label>
                        <input type="number"
                               min={BLOCK_NUMBER.MIN}
                               max={BLOCK_NUMBER.MAX}
                               defaultValue={BLOCK_NUMBER.MIN}
                               name="startBlock" />
                    </div>
                    <div className="toolbar__entry">
                        <label>End block: </label>
                        <input type="number"
                               min={BLOCK_NUMBER.MIN}
                               max={BLOCK_NUMBER.MAX}
                               defaultValue={BLOCK_NUMBER.MAX}
                               name="endBlock" />
                    </div>
                    <button type="submit">Explore</button>
                </form>
                {
                    error &&
                    <div className="error">
                        {error}
                    </div>
                }
                {
                    spinner &&
                    <div className="spinner__wrapper">
                        <img src={fan} className="spinner" />
                    </div>
                }
                {
                    minedBlocksReward > 0 &&
                    <h2 className="reward">
                        <span className="bold">{minedBlocksReward}</span>
                        mined by provided address.
                    </h2>
                }
                {
                    (!error && !spinner && address) &&
                    <Graph {...graphParams} address={address} onClick={this.fetchNodeData} />
                }
            </div>
        );
    }
}

export default App;
