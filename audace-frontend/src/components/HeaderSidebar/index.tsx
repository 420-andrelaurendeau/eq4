import AppHeader from "../AppHeader";
import SidebarRoutes from "../SiderbarRoutes";

const HeaderSidebar: React.FC = () => {
    const showSidebar = () => {

    }
    return (
        <div>
            <AppHeader notificationClick={showSidebar}/>
            <SidebarRoutes/>
        </div>
    );
}

export default HeaderSidebar;