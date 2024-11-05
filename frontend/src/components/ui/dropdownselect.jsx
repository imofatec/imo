import {
    Cloud,
    CreditCard,
    Github,
    Keyboard,
    LifeBuoy,
    LogOut,
    Mail,
    MessageSquare,
    Plus,
    PlusCircle,
    Settings,
    User,
    UserPlus,
    Users,
  } from "lucide-react"
  
  import { Button } from "@/components/ui/button"
  import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuGroup,
    DropdownMenuItem,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
  } from "@/components/ui/dropdown-menu"
import { Arrow } from "./arrow"
import { useState } from "react"
  
  export function DropdownSelect() {
    let arrowState = 'up'
    const [isOpen , setIsOpen] = useState(false)
    if(isOpen){
      arrowState = 'down'
    }else{
      arrowState = 'up'
    }

    return (
      <DropdownMenu open={isOpen} onOpenChange={(open) =>setIsOpen(open)}>
        <DropdownMenuTrigger asChild>
          <Button className='bg-inherit'>Ordenar por<Arrow orientation={arrowState} size={'sm'} ></Arrow> </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent className="w-56">
          
          <DropdownMenuGroup>

            <DropdownMenuItem>
              <span>Upload Mais Antigo</span>
            </DropdownMenuItem>
            <DropdownMenuSeparator className='mx-2' />

            <DropdownMenuItem>
              <span>Upload Mais Recente</span>
            </DropdownMenuItem>
            <DropdownMenuSeparator className='mx-2'  />

            <DropdownMenuItem>
              <span>A - Z Alfabético</span>
            </DropdownMenuItem>
            <DropdownMenuSeparator className='mx-2'  />

            <DropdownMenuItem>
              <span>Z - A Alfabético</span>
            </DropdownMenuItem>
            
          </DropdownMenuGroup>
        </DropdownMenuContent>
      </DropdownMenu>
    )
  }
  