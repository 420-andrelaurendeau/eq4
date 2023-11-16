import { render, screen } from "@testing-library/react";
import CustomMenu from "../../../components/SessionSelector/CustomMenu";
import "@testing-library/jest-dom/extend-expect";
import SelectorOption from "../../../components/SessionSelector/SelectorOption";
import { Session } from "../../../model/session";

it("should render a datepicker", () => {
  render(<CustomMenu style={{}} className="" aria-labelledby="" />);
  const datePicker = screen.getByPlaceholderText(
    /sessionSelector.filterPlaceholder/i
  );
  expect(datePicker).toBeInTheDocument();
});

it("should render a dropdown item", () => {
  const session: Session = {
    id: 1,
    startDate: new Date("September 25, 2021 00:00:00"),
    endDate: new Date("December 25, 2021 00:00:00"),
  };
  const children = <SelectorOption session={session} />;

  render(
    <CustomMenu style={{}} className="" aria-labelledby="">
      {children}
    </CustomMenu>
  );
  const dropdownItem = screen.getByText(/sessionSelector.fall 2021/i);
  expect(dropdownItem).toBeInTheDocument();
});
