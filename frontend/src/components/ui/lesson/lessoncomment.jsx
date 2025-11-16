import { useState } from 'react'
import { Arrow } from '../arrow'
import UserPicture from '../userpicture'
import Commentary from './commentary'

export default function LessonComment({
  profilePic,
  profileName,
  commentTitle,
  commentContent,
  parentId = null,
  lessonId = null,
  children = [],
  isReply = false
}) {
  const [open, setOpen] = useState(false)
  const [arrowOrientation, setArrowOrientation] = useState('down')

  const toggleOpen = () => {
    setOpen(!open)
    setArrowOrientation(open ? 'down' : 'up')
  }

  return (
    <div className="ml-4 my-6">
      <div className="flex flex-row">
        <UserPicture size="md" profilePic={profilePic} />
        <div className="ml-4 w-full">
          <h2 className="font-semibold text-lg">{commentTitle}</h2>
          <p className="my-3 break-all">{commentContent}</p>

          {!isReply && (
            <div className="flex flex-row items-center cursor-pointer" onClick={toggleOpen}>
              <span className="underline text-sm">
                {'Responder'}
              </span>
              <Arrow size="sm" orientation={arrowOrientation} />
            </div>
          )}

          <div className="ml-6">
            {children.map((child) => (
              <LessonComment
                key={child.id}
                profilePic={child.profilePic}
                profileName={child.profileName}
                commentTitle={child.commentTitle}
                commentContent={child.commentContent}
                parentId={child.id}
                lessonId={lessonId}
                children={[]}
                isReply={true}
              />
            ))}
            {open && (
              <Commentary parentId={parentId} lessonId={lessonId} />
            )}
          </div>
        </div>
      </div>
    </div>
  )
}
