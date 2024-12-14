import React from 'react';
import { useNavigate } from 'react-router-dom';
import { logout } from '../services/authService';

const Header = () => {
    const navigate = useNavigate();
    const accessToken = localStorage.getItem('accessToken'); // 检查是否登录
    const role = localStorage.getItem('userRole'); // 获取角色

    const handleLogout = async () => {
        await logout();
        localStorage.clear(); // 清除存储
        navigate('/login'); // 跳转到登录页面
    };

    return (
        <header>
            <nav>
                <ul>
                    <li><a href="/">Home</a></li>
                    {!accessToken && (
                        <li><button onClick={() => navigate('/login')}>Login</button></li>
                    )}
                    {accessToken && role === 'USER' && <li><a href="/profile">Profile</a></li>}
                    {accessToken && role === 'ADMIN' && <li><a href="/admin">Admin Dashboard</a></li>}
                    {accessToken && (
                        <li><button onClick={handleLogout}>Logout</button></li>
                    )}
                </ul>
            </nav>
        </header>
    );
};

export default Header;
