import axios from 'axios'

export const baseUrl = "http://localhost:8080/" 
const api = axios.create({
    baseURL: 'http://localhost:8080/',
    headers: {
        'Content-Type': 'application/json',
        "Access-Control-Allow-Origin": "*"
    },
})

export default api