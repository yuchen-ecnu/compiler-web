// create an array with nodes
var nodes = new vis.DataSet([
    {id: 1, label: 'Node 1'},
    {id: 2, label: 'Node 2'},
    {id: 3, label: 'Node 3'},
    {id: 4, label: 'Node 4'},
    {id: 5, label: 'Node 5'},
    {id: 6, label: 'Node 6'},
    {id: 7, label: 'Node 7'},
    {id: 8, label: 'Node 8'}
]);

// create an array with edges
var edges = new vis.DataSet([
    {from: 1, to: 8, arrows:'to'},
    {from: 1, to: 3, arrows:'to'},
    {from: 1, to: 2, arrows:'to, from'},
    {from: 3, to: 3, arrows:'to'},
    {from: 2, to: 4, arrows:'to'},
    {from: 2, to: 5, arrows:'to'},
    {from: 5, to: 6, arrows:'to, from'},
    {from: 6, to: 7, arrows:'to, from'}
]);

// create a network
var container = document.getElementById('mynetwork');
var data = {
    nodes: nodes,
    edges: edges
};
var options = {};
var network = new vis.Network(container, data, options);

/***
 原先结点颜色
 改变后结点颜色
***/
var newColor = '#86b';
var oldColor = '#86b3fb'

function sleep(d){
    for(var t = Date.now();Date.now() - t <= d;);
}

function changeNodeColor(nodeId, delayTime) {
    setTimeout(function(){
        nodes.update([{id:nodeId, color:{background:newColor}}]);
    },delayTime);
}

function changeNodesColor() {
    changeNodeColor(3, 1000);
    changeNodeColor(5, 2000);
    changeNodeColor(4, 3000);
}

