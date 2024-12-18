import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import AppRoutes from './routes'; // 引入路由配置
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import './styles/index.css'; // 全局样式

const App = () => {
    return (
        <Router>
            <Navbar />
            <AppRoutes /> {/* 加载路由 */}
            <Footer />
        </Router>
    );
};

export default App;
