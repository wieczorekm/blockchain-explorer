import React, {Component} from 'react';
import Graph from './components/graph/Graph';
import './App.css';

class App extends Component {

    constructor(props) {
        super(props);
            this.state = {
            message: ""
        };
    }

    componentDidMount() {
        fetch("https://blockchain-explorer-was-taken.herokuapp.com/hello") // move api url to package.json
            .then(response => response.text())
            .then(response => this.setState({message: response}));
    }

    render() {
        const {message} = this.state;
        return (
            <div>
                <h1>Blockchain Explorer {message}</h1>
                <select>
                    <option>Etherum</option>
                    <option>Bitcoin</option>
                </select>
                <input />
                <button>Explore</button>
                <Graph />
            </div>
        );
    }
}

export default App;
