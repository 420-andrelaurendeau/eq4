import {
  CSSProperties,
  Children,
  ReactNode,
  forwardRef,
  useState,
} from "react";
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";
import { useTranslation } from "react-i18next";

interface Props {
  children: ReactNode;
  style: CSSProperties;
  className: string;
  "aria-labelledby": string;
}

const CustomMenu = forwardRef<HTMLDivElement, Props>(
  ({ children, style, className, "aria-labelledby": labelledby }, ref) => {
    const [filterValue, setFilterValue] = useState<Date>();
    const { t } = useTranslation();

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
        {Children.toArray(children).filter((child) => {
          const newStartDate = new Date((child as any).props.session.startDate);

          return !filterValue || newStartDate <= filterValue;
        })}
      </div>
    );
  }
);

export default CustomMenu;
