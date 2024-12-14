import React from 'react';
import ReactDOM from 'react-dom/client'; // 注意：使用 react-dom/client 而不是 react-dom
import './index.css';
import App from './App';

// 找到 HTML 中的 root 元素
const rootElement = document.getElementById('root');

// 使用 React 18 的 createRoot API
const root = ReactDOM.createRoot(rootElement);

// 渲染 App 组件
root.render(
    <React.StrictMode>
        <App />
    </React.StrictMode>
);
