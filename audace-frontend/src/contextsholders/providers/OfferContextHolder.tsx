import { createContext, useContext, useState } from "react";
import { Offer } from "../../model/offer";

interface OfferContextType {
  offers: Offer[];
  setOffers: (newOffer: Offer[]) => void;
}

export const OfferContext = createContext<OfferContextType>({
  offers: [],
  setOffers: () => {},
});

export const useOfferContext = () => useContext(OfferContext);

export function OfferProvider({ children }: any) {
  const [offers, setOffers] = useState<Offer[]>([]);

  return (
    <OfferContext.Provider value={{ offers, setOffers }}>
      {children}
    </OfferContext.Provider>
  );
}
