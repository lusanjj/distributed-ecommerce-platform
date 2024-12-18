import React from 'react';
import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux';
import store from './redux/store'; // Redux Store 配置
import App from './App'; // 主应用组件
import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import translationEN from './locales/en.json';
import './styles/index.css'; // 全局样式

// 国际化配置
i18n.use(initReactI18next).init({
    resources: {
        en: { translation: translationEN },
    },
    lng: 'en', // 默认语言
    fallbackLng: 'en',
    interpolation: { escapeValue: false },
});

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <React.StrictMode>
        <Provider store={store}>
            <App />
        </Provider>
    </React.StrictMode>
);
