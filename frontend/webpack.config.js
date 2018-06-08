var packageJSON = require('./package.json');
var path = require('path');
var webpack = require('webpack');

const PATHS = {
    build: path.join(__dirname, 'target', 'classes', 'META-INF', 'resources', 'webjars', packageJSON.name, packageJSON.version)
};

module.exports = {
    entry: './src/index.js',
    output: {
        path: PATHS.build,
        publicPath: '/assets/',
        filename: 'app-bundle.js'
    },
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /node_modules/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ['stage-0', 'react']
                    }
                }]
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.(pdf|jpg|png|gif|svg|ico|ttf)$/,
                use: [
                    {
                        loader: 'url-loader'
                    },
                ]
            },
        ]
    }
};