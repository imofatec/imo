import {
    AccordionContent,
    AccordionItem,
    AccordionTrigger,
  } from "@/components/ui/dropdown/accordion"
import { Seletor } from "./seletor"
  
export default function ItemDropdown( {numItem , labelItem , contItem} ){
    return <>
        <AccordionItem value={numItem}>
          <AccordionTrigger>{labelItem}</AccordionTrigger>
          <AccordionContent>
            {contItem}
            <Seletor></Seletor>
            <Seletor></Seletor>
            <Seletor></Seletor>

          </AccordionContent>
        </AccordionItem>
    </>
}