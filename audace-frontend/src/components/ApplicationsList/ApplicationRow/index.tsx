import Application from "../../../model/application";

interface Props {
  application: Application;
}

const ApplicationRow = ({ application }: Props) => {
  return (
    <tr>
      <td>{application.offer!.title}</td>
      <td>{application.offer!.employer.organisation}</td>
    </tr>
  );
};

export default ApplicationRow;
