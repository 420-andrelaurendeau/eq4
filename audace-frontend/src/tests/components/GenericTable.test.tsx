import { render, screen } from "@testing-library/react";
import GenericTable from "../../components/GenericTable";

it("should render the list of strings", () => {
  const stringList = ["test1", "test2", "test3"];
  const children = stringList.map((string) => <p key={string}>{string}</p>);
  render(
    <GenericTable
      list={stringList}
      error=""
      emptyListMessage=""
      title="Generic table test"
    >
      <thead></thead>
      <tbody>
        <tr>
          <td>{children}</td>
        </tr>
      </tbody>
    </GenericTable>
  );

  const linkElement = screen.getByText(/test1/i);
  expect(linkElement).not.toBeUndefined();
  const linkElement2 = screen.getByText(/test2/i);
  expect(linkElement2).not.toBeUndefined();
  const linkElement3 = screen.getByText(/test3/i);
  expect(linkElement3).not.toBeUndefined();

  const title = screen.getByText(/Generic table test/i);
  expect(title).not.toBeUndefined();
});

it("should render an empty list message", () => {
  const stringList: string[] = [];
  const children = stringList.map((string) => <>{string}</>);
  render(
    <GenericTable
      list={stringList}
      error=""
      emptyListMessage="Empty list message"
      title="Generic table test"
    >
      {children}
    </GenericTable>
  );

  const linkElement = screen.getByText(/Empty list message/i);
  expect(linkElement).not.toBeUndefined();
});

it("should render an error message", () => {
  const stringList: string[] = [];
  const children = stringList.map((string) => <>{string}</>);
  render(
    <GenericTable
      list={stringList}
      error="Error message"
      emptyListMessage="Empty list message"
      title="Generic table test"
    >
      {children}
    </GenericTable>
  );

  const linkElement = screen.getByText(/Error message/i);
  expect(linkElement).not.toBeUndefined();
});
