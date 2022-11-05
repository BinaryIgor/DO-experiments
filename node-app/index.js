import http from "http";

const port = process.PORT | 8080;

const server = http.createServer((req, res) => {
    res.statusCode = 200;
    res.setHeader('Content-Type', "text/plain");
    res.end(`Just some node server: ${new Date()}`);
});

server.listen(port, () => console.log("Server has started!"));