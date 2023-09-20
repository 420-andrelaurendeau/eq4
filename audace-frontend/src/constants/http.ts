import axios from 'axios';

const http = axios.create({
    baseURL: 'http://localhost:8080',
    headers: { 'Content-type' : 'Application/JSON',
                'Authorization' : ''}
})

export default http;