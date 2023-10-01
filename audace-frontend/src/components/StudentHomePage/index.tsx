import { Button } from "react-bootstrap";
import { useParams } from "react-router-dom";

const StudentHomePage = () => {
    const { userId } = useParams();

    return (
        <div>
            <h1>Student {userId}</h1>
            <Button href={`/student/${userId}/offers`}>Voir les offres</Button>
        </div>
    );
};

export default StudentHomePage;