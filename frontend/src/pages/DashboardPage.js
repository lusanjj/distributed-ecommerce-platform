import React, { useEffect, useState } from "react";
import api from "../services/api";

const DashboardPage = () => {
    const [userData, setUserData] = useState(null);

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const response = await api.get("/users/me");
                setUserData(response.data);
            } catch (err) {
                console.error("Failed to fetch user data", err);
            }
        };

        fetchUserData();
    }, []);

    return (
        <div>
            <h1>Dashboard</h1>
            {userData ? (
                <div>
                    <p>Welcome, {userData.username}</p>
                </div>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );
};

export default DashboardPage;
