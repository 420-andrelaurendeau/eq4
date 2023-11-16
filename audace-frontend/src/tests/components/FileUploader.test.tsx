import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import FileUploader from "../../components/FileUploader";
import { Student } from "../../model/user";

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

it("should have a submit button", () => {
  render(<FileUploader student={student} />);

  screen.getByText(/upload.submit/i);
});

it("should have a file input", () => {
  render(<FileUploader student={student} />);

  screen.getByLabelText(/upload.file/i);
});

it("should submit a file", async () => {
  render(<FileUploader student={student} />);

  const str = JSON.stringify({ name: "test" });
  const blob = new Blob([str], { type: "application/json" });
  const file = new File([blob], "test.json", { type: "application/json" });

  const input = screen.getByLabelText(/upload.file/i) as HTMLInputElement;

  fireEvent.change(input, { target: { files: [file] } });
  await waitFor(() => expect(input.files).toHaveLength(1));

  fireEvent.click(screen.getByText(/upload.submit/i));

  await screen.findByText(/upload.success/i);
});

it("should not submit without a file", async () => {
  render(<FileUploader student={student} />);

  fireEvent.click(screen.getByText(/upload.submit/i));
});

it("should pop an alert on submit failure", async () => {
  jest
    .spyOn(require("../../services/fileService"), "uploadFile")
    .mockImplementation(() => Promise.reject("error"));

  render(<FileUploader student={student} />);

  const str = JSON.stringify({ name: "test" });
  const blob = new Blob([str], { type: "application/json" });
  const file = new File([blob], "test.json", { type: "application/json" });

  const input = screen.getByLabelText(/upload.file/i) as HTMLInputElement;

  fireEvent.change(input, { target: { files: [file] } });
  await waitFor(() => expect(input.files).toHaveLength(1));

  fireEvent.click(screen.getByText(/upload.submit/i));

  await screen.findByText(/upload.error/i);
});
