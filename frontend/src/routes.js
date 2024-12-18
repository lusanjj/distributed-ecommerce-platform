import React from 'react';
import { Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import SignUpPage from './pages/SignUpPage';
import DashboardPage from './pages/DashboardPage';
import AdminDashboard from './pages/AdminDashboard';
import ErrorPage from './pages/ErrorPage';
import PrivateRoute from './components/PrivateRoute'; // 保护路由

const AppRoutes = () => {
    return (
        <Routes>

            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/signup" element={<SignUpPage />} />
            <Route path="/dashboard" element={<PrivateRoute component={DashboardPage} />} />
            <Route path="/admin/dashboard" element={<PrivateRoute component={AdminDashboard} />} />
            <Route path="*" element={<ErrorPage />} />
        </Routes>
    );
};

export default AppRoutes;
