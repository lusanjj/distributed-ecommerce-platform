import axios from "axios";

// Base URL of your backend user-service
const API_BASE_URL = "http://localhost:8080/api";

// Create an Axios instance
const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        "Content-Type": "application/json",
    },
});

// Interceptors to handle auth tokens if needed
api.interceptors.request.use((config) => {
    const token = localStorage.getItem("accessToken");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default api;
