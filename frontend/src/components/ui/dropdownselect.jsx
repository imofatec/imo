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
  
  export function DropdownSelect({onSelectOrder}) {
    let arrowState = 'up'
    const [isOpen , setIsOpen] = useState(false)
    if(isOpen){
      arrowState = 'down'
    }else{
      arrowState = 'up'
    }

    const handleOrderSelect = (order) => {
      onSelectOrder(order) 
    }

    return (
      <DropdownMenu open={isOpen} onOpenChange={(open) =>setIsOpen(open)}>
        <DropdownMenuTrigger asChild>
          <Button className='bg-inherit'>Ordenar por<Arrow orientation={arrowState} size={'sm'} ></Arrow> </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent className="w-56">
          
          <DropdownMenuGroup>

            <DropdownMenuItem onClick={() => handleOrderSelect('oldest')}> 
              <span>Mais antigo</span>
            </DropdownMenuItem>
            <DropdownMenuSeparator className='mx-2' />

            <DropdownMenuItem onClick={() => handleOrderSelect('newest')}>
              <span>Mais recente</span>
            </DropdownMenuItem>
            <DropdownMenuSeparator className='mx-2'  />

            <DropdownMenuItem onClick={() => handleOrderSelect('az')}>
              <span>A - Z</span>
            </DropdownMenuItem>
            <DropdownMenuSeparator className='mx-2'  />

            <DropdownMenuItem onClick={() => handleOrderSelect('za')}>
              <span>Z - A</span>
            </DropdownMenuItem>
            
          </DropdownMenuGroup>
        </DropdownMenuContent>
      </DropdownMenu>
    )
  }
  