import { useState } from "react";
import AppHeader from "../AppHeader";
import SidebarRoutes from "../SiderbarRoutes";

const HeaderSidebar: React.FC = () => {
    const [showNotifications, setShowNotifications] = useState<boolean>(false)
    return (
        <div>
            <AppHeader showNotifications={showNotifications} setShowNotifications={setShowNotifications}/>
            <SidebarRoutes showNotifications={showNotifications}/>
        </div>
    );
}

export default HeaderSidebar;