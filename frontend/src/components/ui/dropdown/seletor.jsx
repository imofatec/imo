"use client"

import { Checkbox } from "@/components/ui/checkbox"
export function Seletor({ contSeletor }) {
  contSeletor = "Lorem Ipsum"

  return (
    <div className="flex items-center space-x-12 justify-center p-2">
      <Checkbox id="" />
      <label
        htmlFor=""
        className="text-sm text-center font-medium leading-none  peer-disabled:opacity-70" >
        {contSeletor}
      </label>
    </div>
  )
}
