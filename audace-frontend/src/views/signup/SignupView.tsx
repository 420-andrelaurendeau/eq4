import { Container } from "react-bootstrap";
import StudentSignup from "../../components/signup/StudentSignup";

const SignupView = () => {
    return (
        <Container>
            <h2>Signup</h2>
            <StudentSignup />
        </Container>
    )
};

export default SignupView;