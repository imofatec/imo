import { useState } from 'react'
import { Arrow } from '../arrow'
import UserPicture from '../userpicture'
import Commentary from './commentary'

export default function LessonComment({
  profilePic,
  profileName,
  commentTitle,
  commentContent,
  reply,
}) {
  commentContent =
    'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Velit ratione veritatis vitae a voluptas quibusdam vero veniam, molestiae, ducimus natus quasi alias laboriosam officia eos minus. Est temporibus dolores hic.'
  profileName = 'Nome'
  commentTitle = 'Lorem ipsum dolor sit amet'
  reply = 3
  const [open, setOpen] = useState(false)
  const [arrowOrientation, setArrowOrientation] = useState('down')

  const openComment = () => {
    if (!open) {
      setOpen(true)
      setArrowOrientation('up')
    } else {
      setOpen(false)
      setArrowOrientation('down')
    }
  }
  return (
    <div className="flex flex-row my-10">
      <div className="flex flex-col ">
        <div className="flex flex-row text-center">
          <label>
            <UserPicture size={'md'} profilePic={profilePic}></UserPicture>
            {profileName}
          </label>
        </div>
      </div>
      <div className="flex flex-col ml-12 ">
        <h2 className="font-semibold text-lg">{commentTitle}</h2>
        <p className="my-3">{commentContent}</p>
        <div className="flex flex-row" onClick={openComment}>
          <a className="ml-6 underline">
            {reply ? (
              <>{reply} respostas</>
            ) : (
              <>Seja o primeiro a responder esse cometário!</>
            )}
          </a>
          <Arrow size={'sm'} orientation={arrowOrientation}></Arrow>
        </div>
        <div className="w-full">
          {open ? (
            reply ? (
              <>
                {/*
              listaRespostas.map((data , i) => {
                    return (
                      <LessonComment
                        profilePic={xis}
                        profileName={item.profileName}
                        commentContent={item.commentContent}
                        commentTitle={item.commentTitle}
                      ></LessonComment>
                    )
                  })
                })
              */}
                <Commentary></Commentary>
              </>
            ) : (
              <Commentary></Commentary>
            )
          ) : (
            <></>
          )}
        </div>
      </div>
    </div>
  )
}
