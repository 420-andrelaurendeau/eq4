import {
  CSSProperties,
  Children,
  ReactNode,
  forwardRef,
  useState,
} from "react";
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";

interface Props {
  children: ReactNode;
  style: CSSProperties;
  className: string;
  "aria-labelledby": string;
}

const CustomMenu = forwardRef<HTMLDivElement, Props>(
  ({ children, style, className, "aria-labelledby": labelledby }, ref) => {
    const [filterValue, setFilterValue] = useState<Date>(new Date());

    return (
      <div
        ref={ref}
        style={style}
        className={className}
        aria-labelledby={labelledby}
      >
        <DatePicker
          selected={filterValue}
          onChange={(date: Date) => setFilterValue(date)}
        />
        {Children.toArray(children).filter((child) => {
          const newStartDate = new Date((child as any).props.session.startDate);

          return (
            !filterValue ||
            (newStartDate <= filterValue)
          );
        })}
      </div>
    );
  }
);

export default CustomMenu;
