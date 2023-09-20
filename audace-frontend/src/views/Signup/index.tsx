import { Container } from "react-bootstrap";
import StudentSignup from "../../components/Signup/StudentSignup";
import { UserType } from "../../model/user";
import EmployerSignup from "../../components/Signup/EmployerSignup";

interface Props {
  userType: UserType;
}

const SignupView = ({userType}: Props) => {
  const determineSignupForm = (): JSX.Element => {
    switch (userType) {
      case UserType.Student:
        return <StudentSignup />;
      case UserType.Employer:
        return <EmployerSignup />;
      default:
        return <></>;
    }
  }

  return (
    <Container>
      {determineSignupForm()}
    </Container>
  );
};

export default SignupView;
