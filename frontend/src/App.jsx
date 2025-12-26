import { useState, useEffect } from 'react'
import axios from 'axios'
import './App.css'
import { Outlet } from "react-router-dom";

import MainPage from './pages/MainPage';
import Header from './components/common/Header';

function App() {
  return (
    <div className="app-layout">
      <Header/>
      <main className="app-main">
        <Outlet />
      </main>
    </div>
  );
}

export default App
