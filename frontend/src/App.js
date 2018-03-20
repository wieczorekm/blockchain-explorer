import React, {Component} from 'react';
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
            <div>{message} Hello also from React!</div>
        );
    }
}

export default App;
