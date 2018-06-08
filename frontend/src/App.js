import React, { Component } from 'react';
import _ from 'lodash';

import Graph from './components/graph/Graph';

import './App.css';
import link from './assets/link.svg';
import fan from './assets/fan.svg';

const API_URL = "https://blockchain-explorer-was-taken.herokuapp.com";
const BLOCKCHAIN_TYPE = 'ethereum';
const BLOCK_NUMBER = { MIN: 0, MAX: 9999999 };

class App extends Component {
    constructor(props) {
        super(props);
        this.state = { startBlock: BLOCK_NUMBER.MIN, endBlock: BLOCK_NUMBER.MAX };
    }

    setType = ({ target: { value } }) => this.setState({ type: value });

    renderOption = (type) => <option value={type} key={type}>{_.capitalize(type)}</option>;

    setAddress = ({ target: { value } }) => this.setState({ address: value });

    setStartBlock = ({ target: { value } }) => this.setState({ startBlock: value });

    setEndBlock = ({ target: { value } }) => this.setState({ endBlock: value });

    fetchData = () => {
        const { address, startBlock, endBlock } = this.state;

        if (!address) {
            this.setState({ error: 'No address provided' });
            return;
        }

        this.setState({ spinner: true });

        fetch(`${API_URL}/${BLOCKCHAIN_TYPE}/transactions/${address.toLowerCase()}?startBlock=${startBlock}&endBlock=${endBlock}`)
            .then((response) => {
                if (!response.ok) {
                    throw response;
                }

                return response.json();
            })
            .then(response => this.setState({ ...response, error: null, spinner: false }))
            .catch((error) => error
                .json()
                .then(({ message }) => this.setState({ error: message, spinner: false }))
            );
    }

    render() {
        const { error, spinner, minedBlocksReward, ...graphParams } = this.state;
        return (
            <div className="app">
                <div className="logo">
                    <img src={link} />
                    <h1>
                        Blockchain Explorer
                    </h1>
                </div>
                <div className="toolbar">
                    <h2>
                        Explore
                        <span className="bold">{BLOCKCHAIN_TYPE}</span>
                        blockchain
                    </h2>
                    <div className="toolbar__entry">
                        <label>Address: </label>
                        <input onChange={this.setAddress} />
                    </div>
                    <div className="toolbar__entry">
                        <label>Start block: </label>
                        <input type="number"
                               min={BLOCK_NUMBER.MIN}
                               max={BLOCK_NUMBER.MAX}
                               defaultValue={BLOCK_NUMBER.MIN}
                               onChange={this.setStartBlock} />
                    </div>
                    <div className="toolbar__entry">
                        <label>End block: </label>
                        <input type="number"
                               min={BLOCK_NUMBER.MIN}
                               max={BLOCK_NUMBER.MAX}
                               defaultValue={BLOCK_NUMBER.MAX}
                               onChange={this.setEndBlock} />
                    </div>
                    <button onClick={this.fetchData}>Explore</button>
                </div>
                {
                    error &&
                    <div className="error">
                        {error}
                    </div>
                }
                {
                    spinner && <img src={fan} className="spinner" />
                }
                {
                    minedBlocksReward &&
                    <h2 className="reward">
                        <span className="bold">{minedBlocksReward}</span>
                        mined by provided address.
                    </h2>
                }
                {
                    !error &&
                    <Graph {...graphParams} />
                }
            </div>
        );
    }
}

export default App;
