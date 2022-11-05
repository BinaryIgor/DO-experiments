import http from "http";

const port = process.env.PORT || 8080;
const secretParam = process.env.SECRET_PARAM || "default X";

const server = http.createServer((req, res) => {
    console.log("Getting request...", req.headers);
    res.statusCode = 200;
    res.setHeader('Content-Type', "text/plain");
    res.end(`Just some node server, asked at: ${new Date()}, with env: ${secretParam}`);
});

server.listen(port, () => console.log("Server has started!"));