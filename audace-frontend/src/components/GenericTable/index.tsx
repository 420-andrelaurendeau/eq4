import { ReactNode } from "react";
import SessionSelector from "../SessionSelector";
import { useTranslation } from "react-i18next";
import { Table } from "react-bootstrap";

interface Props<T> {
  list: T[];
  error: string;
  children: ReactNode;
  emptyListMessage: string;
}

const GenericTable = <T,>({
  list,
  error,
  children,
  emptyListMessage,
}: Props<T>) => {
  const { t } = useTranslation();

  return (
    <>
      <SessionSelector />
      {error !== "" ? (
        <p className="text-center">{error}</p>
      ) : list.length > 0 ? (
        <Table striped bordered hover size="sm">
          {children}
        </Table>
      ) : (
        <p className="text-center">{t(emptyListMessage)}</p>
      )}
    </>
  );
};

export default GenericTable;
