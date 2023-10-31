import { render, screen } from "@testing-library/react";
import FileUploader from "../../components/FileUploader";
import { Student } from "../../model/user";

it("should render normally", () => {
  const student: Student = {
    studentNumber: "1234567890",
    firstName: "John",
    lastName: "Doe",
    email: "yaintizit@email.com",
    phone: "1234567890",
    address: "1234 Main St",
    password: "password",
    type: "student",
  };
  render(<FileUploader student={student} />);

  screen.getByText(/Téléverser votre CV/i);
});
