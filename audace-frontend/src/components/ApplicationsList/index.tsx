import { Table } from "react-bootstrap";
import Application from "../../model/application";
import ApplicationRow from "./ApplicationRow";

interface Props {
  applications: Application[];
}

const ApplicationsList = ({ applications }: Props) => {
  return (
    <>
      <h2>ApplicationsList</h2>
      <Table striped bordered hover size="sm">
        <thead>
          <tr>
            <th>Position title</th>
            <th>Organization</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {applications.map((application) => (
            <ApplicationRow key={application.id} application={application} />
          ))}
        </tbody>
      </Table>
    </>
  );
};

export default ApplicationsList;
