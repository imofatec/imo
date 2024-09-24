import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'

export default function UserPicture({ profilePic, size }) {
  switch (size) {
    case 'md':
      size = 'w-16 h-16'
      break
    case 'lg':
      size = 'w-21 h-21'
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
