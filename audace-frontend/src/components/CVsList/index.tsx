import { useTranslation } from "react-i18next";
import { UserType } from "../../model/user";
import { CV, CVStatus } from "../../model/cv";
import CvRow from "./CvRow";
import GenericTable from "../GenericTable";
import { getUserType } from "../../services/authService";

interface Props {
  cvs: CV[];
  error: string;
  updateCvsState?: (cv: CV, cvStatus: CVStatus) => void;
}

const CvsList = ({ cvs, error, updateCvsState }: Props) => {
  const { t } = useTranslation();
  const userType = getUserType();

  return (
    <>
      <GenericTable
        list={cvs}
        error={error}
        emptyListMessage="cvsList.noCvs"
        title="cvsList.name"
      >
        <thead>
          <tr>
            {userType !== UserType.Student && (
              <th>{t("cvsList.studentName")}</th>
            )}
            <th>{t("cvsList.name")}</th>
            {userType !== UserType.Student && <th></th>}
          </tr>
        </thead>
        <tbody>
          {cvs.map((cv) => {
            return (
              <CvRow key={cv.id} cv={cv} updateCvsState={updateCvsState} />
            );
          })}
        </tbody>
      </GenericTable>
    </>
  );
};

export default CvsList;
