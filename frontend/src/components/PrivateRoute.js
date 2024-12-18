import React from 'react';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ component: Component }) => {
    const token = localStorage.getItem('accessToken'); // 检查是否有 Token

    // 渲染传入的组件
    return token ? <Component /> : <Navigate to="/login" replace />;
};

export default PrivateRoute;
