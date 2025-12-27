import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './styles/global.css'
import { Routes, Route, BrowserRouter } from 'react-router-dom'

import App from './App.jsx'
import MainPage from './pages/MainPage.jsx'
import Login from './components/common/Login.jsx'
import SignUp from './components/common/SignUp.jsx'

createRoot(document.getElementById('root')).render(
  // <StrictMode>
  //   <App />
  // </StrictMode>,
  <BrowserRouter>
    <App />
  </BrowserRouter>
)
