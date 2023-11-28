import { Offer } from "../../model/offer";
import Application, { ApplicationStatus } from "../../model/application";
import { Container } from "react-bootstrap";
import ApplicationsList from "../ApplicationsList";

interface Props {
  applications: Application[];
  error: string;
  updateAvailablePlaces?: (offer: Offer) => void;
  updateApplicationsState?: (
    application: Application,
    applicationStatus: ApplicationStatus
  ) => void;
}

const Applications = ({
  applications,
  error,
  updateApplicationsState,
}: Props) => {
  return (
    <Container>
      {
        <ApplicationsList
          applications={applications}
          error={error}
          updateApplicationsState={updateApplicationsState}
        />
      }
    </Container>
  );
};

export default Applications;
