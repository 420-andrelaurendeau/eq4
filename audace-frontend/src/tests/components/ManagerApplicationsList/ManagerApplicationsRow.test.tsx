import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import ManagerApplicationRow from "../../../components/ManagerApplicationsList/ManagerApplicationRow";
import { application } from "../testUtils/testUtils";
import { Table } from "react-bootstrap";
import "@testing-library/jest-dom/extend-expect";

it("should render application properties", () => {
  render(
    <Table>
      <tbody>
        <ManagerApplicationRow application={application} />
      </tbody>
    </Table>
  );

  expect(screen.getByText(application.offer!.title)).toBeInTheDocument();
  expect(
    screen.getByText(application.offer!.employer.organisation)
  ).toBeInTheDocument();
  expect(
    screen.getByText(
      `${application.cv!.student!.firstName} ${
        application.cv!.student!.lastName
      }`
    )
  ).toBeInTheDocument();

  expect(screen.getByText(/manager.createContractButton/i)).toBeInTheDocument();
});

it("should call useNavigate on button click", async () => {
  const mockedUseNavigate = jest.fn();

  jest
    .spyOn(require("react-router-dom"), "useNavigate")
    .mockImplementation(() => mockedUseNavigate);

  render(
    <Table>
      <tbody>
        <ManagerApplicationRow application={application} />
      </tbody>
    </Table>
  );

  const button = screen.getByText(/manager.createContractButton/i);
  fireEvent.click(button);

  await waitFor(() => expect(mockedUseNavigate).toHaveBeenCalledTimes(1));
});
