import {
  CSSProperties,
  Children,
  PropsWithChildren,
  forwardRef,
  useState,
} from "react";
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";
import { useTranslation } from "react-i18next";

interface Props {
  style: CSSProperties;
  className: string;
  "aria-labelledby": string;
}

const CustomMenu = forwardRef<HTMLDivElement, PropsWithChildren<Props>>(
  ({ children, style, className, "aria-labelledby": labelledby }, ref) => {
    const [filterValue, setFilterValue] = useState<Date>();
    const { t } = useTranslation();

    const elementsToDisplay = Children.toArray(children).filter((child) => {
      const newStartDate = new Date((child as any).props.session.startDate);

      return !filterValue || newStartDate <= filterValue;
    });

    return (
      <div
        ref={ref}
        style={style}
        className={className}
        aria-labelledby={labelledby}
      >
        <DatePicker
          showIcon={true}
          selected={filterValue}
          onChange={(date: Date) => setFilterValue(date)}
          dateFormat={"dd/MM/yyyy"}
          isClearable
          placeholderText={t("sessionSelector.filterPlaceholder")}
        />
        {elementsToDisplay.length > 0 ? (
          elementsToDisplay
        ) : (
          <div className="dropdown-item text-center">
            {t("sessionSelector.noSessionsFound")}
          </div>
        )}
      </div>
    );
  }
);

export default CustomMenu;
