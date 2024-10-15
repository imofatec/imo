import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'

export default function UserPicture({ profilePic, size }) {
  switch (size) {
    case 'md':
      size = 'w-16 h-16'
      break
    case 'lg':
      size = 'w-20 h-20'
      break
    case 'xl':
      size = 'w-24 h-24'
      break
    default:
      size = ''
      break
  }
  return (
    <Avatar className={size}>
      <AvatarImage src={profilePic} />
      <AvatarFallback>LD</AvatarFallback>
    </Avatar>
  )
}
