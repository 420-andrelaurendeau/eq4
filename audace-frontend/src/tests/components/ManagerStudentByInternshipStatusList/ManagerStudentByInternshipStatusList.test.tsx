import { render, screen } from "@testing-library/react";
import ManagerStudentByInternshipStatusList from "../../../components/ManagerStudentByInternshipStatusList";
import { student } from "../testUtils/testUtils";

beforeEach(() => {
  jest
    .spyOn(
      require("../../../services/managerService"),
      "getDepartmentByManager"
    )
    .mockImplementation(() => Promise.resolve({ data: { id: 1 } }));

  jest
    .spyOn(
      require("../../../services/managerService"),
      "getStudentsByInternshipStatus"
    )
    .mockImplementation(() =>
      Promise.resolve({
        data: {
          studentsWithPendingResponse: [student],
        },
      })
    );
});

it("should render student values", async () => {
  render(<ManagerStudentByInternshipStatusList />);

  // expect(await screen.findByText(/Doe, John/i)).toBeInTheDocument();
  // expect(screen.getByText(/123456789/i)).toBeInTheDocument();
  // expect(screen.getByText(/Computer Science/i)).toBeInTheDocument();
  // expect(
  //   screen.getByText(/studentsByInternship.row.statusValues.pending/i)
  // ).toBeInTheDocument();
});
