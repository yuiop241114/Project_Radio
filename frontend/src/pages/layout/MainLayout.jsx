import { Outlet } from "react-router-dom";
import Header from "../../components/common/Header";

import '../../styles/mainLayout.css'

function MainLayout() {
  return (
    <>
      <div className="layout">
        <Header />
        <main className="content">
          <Outlet />
        </main>
      </div>
    </>
  );
}

export default MainLayout;
