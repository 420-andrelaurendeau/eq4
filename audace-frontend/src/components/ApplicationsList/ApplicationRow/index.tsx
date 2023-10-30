import { useTranslation } from "react-i18next";
import Application from "../../../model/application";
import {useEffect, useState} from "react";
import {getStudentByApplication} from "../../../services/offerService";
import {Student} from "../../../model/user";

interface Props {
  application: Application;
}

const ApplicationRow = ({ application }: Props) => {
  const { t } = useTranslation();

  return (
    <tr>
      <td>{application.offer!.title}</td>
      <td>{application.offer!.employer.organisation}</td>
      <td>
          {application.student!.firstName} {application.student!.lastName}
      </td>
    </tr>
  );
};

export default ApplicationRow;
