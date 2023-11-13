import { render, screen } from "@testing-library/react";
import AddContract from "../../components/AddContract";
import { application, contract } from "./testUtils/testUtils";
import "@testing-library/jest-dom/extend-expect";

beforeEach(() => {
  jest
    .spyOn(require("react-router"), "useParams")
    .mockReturnValue({ applicationId: "1" });

  jest
    .spyOn(require("../../services/managerService"), "getApplicationById")
    .mockResolvedValue({ data: application });

  jest
    .spyOn(
      require("../../services/contractService"),
      "getContractByApplicationId"
    )
    .mockResolvedValue({ data: null });
});

it("should display the values", () => {
  render(<AddContract />);

  expect(screen.getByText(/manager.createContract.title/i)).toBeInTheDocument();
});
