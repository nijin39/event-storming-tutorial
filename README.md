# Event Storming and DDD

(principles for building Spring applications with DDD)

https://www.youtube.com/watch?v=k8sGf6ZPs2E



*Principles for delivering software faster while increasing reliability*:

보다 신뢰성 있는 소프트웨어를 빠르게 제공하는 원칙들.



- UNDERSTAND

  Help teams understand and fix the gap between complex business problems (the so-called "domain") and the model in code representing it. The most common problem I run into is that the domain models that find their way to production are often far and away from what the domain experts had in mind.

  팀이 비즈니스(소위 도메인이라고 불리는)와 코드로 나타내어지는 모델의 차이를 이해하고 수정하도록 돕는 것이 중요합니다. 가장 일반적으로 발생하는 문제는 도메인 모델로 개발되어진 프로그램이 도메인 전문가들이 생각하고 있던 것과는 거리가 멀다는 것입니다.

- DIVIDE

  Decompose software functionally into modules. By module, I mean any independent piece of our enterprise that could be one or many deployment units. It is crucial that each module be shipped as independent products so that we can apply different architectural styles.

  소프트웨어를 기능적으로 모듈로 분해합니다. 모듈이란 하나 또는 여러개의 배포 단위가 될 수 있는 엔터프라이즈의 독립된 부분을 의미합니다. 서로 다른 아키텍쳐 스타일을 적용 할 수 있도록 각 모듈을 독립적인 제품으로 제공하는 것이 중요합니다. 모듈을 분리하는 방법에 대해서는 Clean Archtecture p104부터 나와 있는 3가지의 원칙을 활용하면 됩니다. REP(The Reuse/Release Equivalence Principle), CCP(The Common Closure Principle), CRP(The Common Reuse Principle)

- IMPLEMENT

  Refactor towards microservices by shifting the mindset from monoliths to distributed systems—or *discouraging* going down that path when it is not necessary!

  Monoliths에서 분산시스템으로 사고방식을 MSA로 전환하거나, 필요하지 않을 때 이러한 방식으로 전환하지 않도록 하십시오. 

- DEPLOY

  Improve the process of delivery by enlarging the awareness of habits like *Test Driven Development*, *Continuous Integration* and *Continuous Delivery*

  테스트 주도형 개발, CI/CD 같은 습관에 대한 인식을 확대함으로 배포 프로세스를 개선하세요.

- BUILD VALUE

  Use Spring Boot and Spring Cloud to shorten the time needed to deliver business value. Allow developers to spend as much time as needed on understanding the business domain itself.

  Spring Boot 및 Spring Cloud를 사용하여 비즈니스 가치를 실현하는 데 필요한 시간을 단축하십시오. 개발에 걸리는 시간을 단축하므로 개발자가 비즈니스 도메인을 이해하는데 필요한 시간을 좀더 많이 확보할 수 있습니다.



## 개념설명

### Domain Model

When it comes to understanding the business that you're building software for, there is no programming framework that can magically help us understand and model a complex domain. I don't expect such a tool to ever materialize, since it is generally impossible to predict how such a domain will evolve and change in the future. There are, however, some common abstract business domains that most should be familiar with—like *sales*, *inventory,* or a *product catalogue*. When it comes to domain modeling from scratch, there's no need to reinvent the wheel. Here is a great resource I recommend for complex domain modeling: [Enterprise Patterns and MDA: Building Better Software with Archetype Patterns and UML](https://www.amazon.com/Enterprise-Patterns-MDA-Building-Archetype/dp/032111230X).

소프트웨어를 구축하려는 비즈니스를 이해하는 때 복잡한 도메인을 이해하고 모델링 하는 것에 대하여 마술처럼 도움을 주는 프로그래밍 프레임 워크는 없습니다. 나는 도메인이 미래에 어떻게 발전하고 변화할 것인지 예측하는 것이 불가능 하기 때문에 이런 프로그래밍 프레임이 나올 것이라고 기대하지 않습니다.  그러나 몇몇 공통적으로 판매,재고, 제품 카탈로그와 같이 익숙해져야 하는 일반적으로 추상화 되어 있는 비즈니스 도메인이 있습니다. 처음부터 도메인 모델링을 할 때 바퀴를 다시 발명할 필요는 없습니다. 복잡한 도메인 모델링을 위해 권장하는 훌륭한 참고자료는 아래와 같습니다.

Enterprise Patterns and MDA: Building Better Software with Archetype Patterns and UML](https://www.amazon.com/Enterprise-Patterns-MDA-Building-Archetype/dp/032111230X).



### Understand, Divide, and Continuously Conquer

When rapidly delivering software, we must not sacrifice how code will be understood by others later. Thankfully, we have a set of principles and practices to help us—in the form of Domain-Driven Design. Personally, I like to think about DDD as a process of iterative learning of the unknown. The side-effect of applying DDD is that we are able to make our code more understandable, extendable, and coherent for both developers and the business. With DDD, it becomes possible to make our source code the single source of truth for how a domain should function. Software functionality is meant to be changed. But when a developer is unable to articulate source code to the business in the terms that they understand, that functionality becomes ornamental and difficult to change or replace.

Even the most complex domains can be divided into…

소프트웨어를 신속하게 제공 할 때 나중에 다른 사람들이 코드를 이해하는 방법을 희생해서는 안됩니다. 고맙게도 우리는 도메인 중심 디자인의 형태로 우리를 도울 일련의 원칙과 관행을 갖고 있습니다. 개인적으로, 저는 DDD가 미지의 것을 반복적으로 학습하는 과정이라고 생각하고 싶습니다. DDD 적용의 부차적이 효과는 개발자와 비즈니스 모두에게 코드를 더 이해하기 쉽고 확장 가능하며 일관성있게 만들 수 있다는 것입니다. DDD를 사용하면 소스 코드를 도메인 작동 방식에 대한 하나의 진리의 원천으로 만들 수 있습니다. 소프트웨어 기능은 항상 변경 됩니다. 그러나 개발자가 이해하는 조건으로 비즈니스에 대한 소스 코드를 명확하게 표현할 수없는 경우 해당 기능은 변경되거나 교체될 수 없는 장식이 될수 밖에 없습니다. 

가장 복잡한 도메인조차도 다음과 같이 나눌 수 있습니다.

- Smaller but still quite complex subdomains (so-called core domains) - this is probably the largest competitive advantage of our enterprise hence, we invest much effort there.

  작지만 가장 복잡한 서브도메인("코어도메인") - 아마도 우리 기업의 경쟁력의 핵심적인 부분으로 많은 노력을 투자해야 합니다.

- Simple and understandable subdomains that might not be unique to our enterprise (so-called generic subdomains) - we need them for our enterprise to operate but it does not give our customers competitive advantage. Think about *inventory* or *invoicing.* Our users will not come back attracted by even the prettiest invoices.

  엔터프라이즈에 고유하지 않은 간단하고 이해하기 쉬운 도메인("generic SubDomain") - 기업용 어플리케이션이 동작하게 도움을 주지만 경쟁 우위를 제공하지는 않습니다. 인벤토리 또는 인보이스에 대해 생각해보십시오. 가장 예쁜 청구서를 만든다고 하여도 고객들은 그다지 좋아하지 않을 것입니다.

![implementing domain-driven design core domainì ëí ì´ë¯¸ì§ ê²ìê²°ê³¼](https://www.oreilly.com/library/view/implementing-domain-driven-design/9780133039900/graphics/02fig02.jpg)

Identifying those smaller products gives us a first draft of how to organize our code into modules. Each subdomain equals separate module. Understanding distinction between core and generic domains helps us see that they probably need different architectural style.

Fortunately, there are a lot of [ingredients we can pick and choose](https://start.spring.io/) from!

이러한 작은 제품들을 확인해보면 우리에게 우리의 코드가 어떻게 모듈로 만들어져야 하는지에 대한 초안을 제공해줍니다. 각각의 서브 도메인은 분리된 모듈과 같은 개념을 가지고 있습니다. 핵심 도메인과 일반 도메인의 차이를 이해하는 것은 우리가 다른 아키텍쳐 스타일이 필요하는 것을 볼 수 있도록 도움을 줍니다. 다행히도 선택할 수 있는 재료가 많이 있습니다.



## 예제(Example)

We are going to implement a simplified credit card management system. We will segment the work to Understand, Divide, Implement and Deploy. The requirements are not clear yet and so far we know that the system should be able to:

우리는 간단한 신용카드 관리 시스템을 만들어볼 것입니다. 우리는 그 작업을 위에서 말했던 대로 Understand, Divide, Implement, Deploy로 나누어서 진행해 볼 것입니다. 요구사항은 아직 까지 명확하지 않습니다. 하지만 우리는 시스템이 아래의 기능들을 수행해야 한다는 사실은 알고 있습니다.



- Assign initial limit to a card : 카드한도액 설정
- Withdraw money : 인출
- Create a statement with amount of money to be repaid (at the end of a billing cycle) : 명세서작성
- Repay money : 환불
- Order or change personalized plastic card : 개인 카드 주문/변경

### Understand

To understand what is **really** going on in our business problem we can take advantage of a lightweight technique called [Event Storming](https://en.wikipedia.org/wiki/Event_storming). All we need is unlimited space on a wide wall, sticky notes and both business and technical people gathered in one room. The first step is to write down **what can happen** in our domain on orange notes. These are the **domain events.** Note the past tense and no particular order.

우리 비즈니스에서 무슨 일이 일어나고 있는지를 이해하고 살펴보기 위해서 우리는 이벤트스톰이라고 불리는 가벼운 기술을 사용할 수 있습니다. 우리에게 필요한 것은 넓은 벽, 포스트잇, 그리고 비즈니스와 엔지니어팀을 가둘수 있는 넓은 방이 필요합니다. 첫 번째 단계는 우리의 도메인에서 일어날 수 있는 일을 오렌지색 노트에 적는 것입니다. 이것이 도메인 이벤트 입니다. 여기에는 과거시제도 없고 특별한 순서도 없습니다.

"도메인 주도 설계 구현 p378" 

> 도메인 전문가가 관심을 가지고 있는 어떤 사건이 발생했다.
>
> 연속된 개별 이벤트를 묶어서 도메인에서 일어나는 활동의 정보를 모델링하자. 각 이벤트를 도메인 객체로 표현하자... 도메인 이벤트는 도메인 모델을 완벽히 지원하며 도메인에서 일어난 어떤 사건을 나타낸다. [Evans, Ref 20]
>
> - "~~할 때"
> - "그런 일이 일어나면..."
> - "..하면 저에게 알려주세요."와 "..하면 저에게 통보해주세요"'
> - "...한 일의 발생"

![events](https://github.com/pilloPl/eventstorming-with-spring/blob/master/just-events.png?raw=true)

Then we must identify the cause of each event. Domain experts know the cause and most probably it can be categorized to:

그리고 나서 각각 이벤트의 원인을 파악해야 합니다. 도메인 전문가들은 원인을 알고 있고, 그것을 대부분 다음과 같이 적절히 분류할 수 있습니다.

- A direct **command** to a system - blue note next to the event
- 시스템에 대한 직접적인 명령 - 이벤트 옆에 파란색으로 표시
- Another event - in that case we put those events next to each other
- 다른 이벤트 - 이 경우 우리는 이벤트를 각각의 이벤트 옆에 표시합니다.
- Some period of time that has passed - small note saying *time*
- 주기적으로 동작해야 하는 이벤트 - time으로 작게 표시합니다.

![events-and-commands](https://github.com/pilloPl/eventstorming-with-spring/blob/master/events-and-causes.png?raw=true)

There is also a green note: *plastic card personalization view*. It is a direct message to the system that causes *plastic card personalization displayed* event. But it is a **query** , not a command. For views and read models we are going to use green notes.

거기에는 개인 카드를 표시해주는 녹색카드도 있습니다. 그것은 시스템에 대한 개인카드를 표시해주는 이벤트의 직접적인 메세지 입니다. 그러나 그것은 명령 모델이 아니고 쿼리모델입니다. 조회와 읽기 모델은 녹색 카드로 표시해줄 수 있습니다.

*CQRS : Command and Query Responsibility Segregation*

> 이름처럼 시스템에서 명령을 처리하는 책임과 조회를 처리하는 책임을 분리하는 것이 CQRS의 핵심입니다. 이제 명령과 조회에 대해 정의할 필요가 있습니다. CQRS에서 명령은 시스템의 상태를 변경하는 작업을 의미하며 조회는 시스템의 상태를 반환하는 작업을 의미합니다. 정리하면, CQRS는 시스템의 상태를 변경하는 작업과 시스템의 상태를 반환하는 작업의 책임을 분리하는 것입니다.

Next step is crucial. We need to know if the cause alone is sufficient for the domain event to occur. Maybe there is another condition that have to be met. Maybe more than one. Those conditions are called **invariants.** If so, we write them down on yellow notes and place in between events and causes.

다음 단계는 매우 중요합니다. 우리는 원인만으로 도메인 이벤트가 발생하는지 충분한지를 알 필요가 있습니다. 아마도 충족시켜야할 다른 조건이 있을 것입니다. 아마도 한계 혹은 여러개의 원인이 있을 수 있습니다. 이러한 조건들은 invariants라고 말합니다. 그렇다면 우리는 그 원인을 적고 노란색 노트로 표시된 원인과 이벤트 사이에 붙여 놓습니다.

![invariants](https://github.com/pilloPl/eventstorming-with-spring/blob/master/invariants.png?raw=true)

If we applied chronology to our events we would get a very good overview of what our domain is about. Moreover, we will learn about basic business processes. The technique is lightweight, quick, fun and more descriptive comparing to tones of text documents or UI mockups. But it did not deliver a single line of code yet, did it?

만약 우리의 이벤트에 대한 연대기를 적용해보면 우리는 우리 도메인이 무엇을 하는지에 대한 좋은 관점을 가질 수 있습니다. 게다가 우리는 기본적인 비즈니스 프로세스에 대하여 배울 수 있습니다. 이 기술은 문서와 UI Mockup과 비교해서 보다 가볍고, 빠르며, 재미있고 좀더 서술적입니다. 그러나 그것은 아직 코드 한줄 우리에게 배달되지 않았습니다. 

### Divide

To find boundaries between business modules we can apply the rule of cohesion: things that change together and are used together should be kept together. For instance, in one module. How can we talk about cohesion having just a set of colorful notes? Let's see.
비즈니스 모듈 사이의 경계를 찾기위해 우리는 결합 규칙을 적용할 수 있습니다.:함께 변화하고 함께 사용되는 것들은 함께 유지되어야 한다. 단지 색깔이 들어있는 노트를 가지고 어떻게 서프트웨어의 응집에 대해서 말할 수 있습니까?

In order to check invariants (yellow notes) system must ask some questions. For instance, in order to withdraw there must be already an assigned limit. System must run a query: *“Hi, does it have assigned limit?”*. On the other hand, there are commands and events that might **change** answer to that question. For instance, the first command to assign limit changes that answer from *no* to *yes* forever. This a clear indicator of **highly cohesive** behaviors that might go together into one module or class.

invariants(노란색 노트)를 확인하기 위해서 우리는 몇가지 질문들을 꼭 자문해볼 필요가 있습니다. 예를 들어, 인출을 하려면 이미 정해진 한도가 있어야 한다. 시스템은 반드시 아래의 쿼리를 수행해야 합니다 "Hi, 정해진 한도를 가지고 있어야 합니까?". 반면 질문에 대한 응답을 변화시키기 위한 명령과 이벤트가 있습니다. 예를 들어, 한계를 할당하기 위한 첫 번째 명령은 '아니오'에서 '예'로 응답하는 것을 영구적으로 변경한다. 이것은 하나의 모듈이나 클래스로 함께 갈 수 있는 고도로 응집력 있는 행동을 나타내는 명확한 지표다.

Let's apply this heuristic in all places. On green notes we will write down the name of a query/view that the system needs to check during processing each invariant. Also, let's highlight when the answer to that query/view might change as a consequence of an event. That way the green notes can be spotted either next to an invariant or next to an event.

이 경험을 모든 곳에서 적용 해 보겠습니다. 초록색 노트에서 각 불변량을 처리하는 동안 시스템이 확인해야하는 쿼리 / 뷰의 이름을 기록합니다. 또한 해당 쿼리 / 뷰에 대한 응답이 이벤트의 결과로 변경 될 때를 강조 표시합니다. 그렇게하면 초록색 메모가 불변성 옆이나 이벤트 옆에 나타납니다.

![invariants-view-events-view-changes](https://i.imgur.com/G9XVk63.png)

Let's search for the following pattern:

위의 그림에서 보면 아래와 같은 패턴을 찾을 수 있습니다.

- Command `CmdA` is fired and it causes `EventA`
- `EventA`로 인하여 `CmdA`가 실행이 되어진다.
- `EventA` affects view `SomeView`.
- `EventA`는 몇몇 뷰`SomeView`에 영향을 미칩니다.
- `SomeView` is also needed while processing an invariant that protects `CmdB`
- `CmdB` 를 처리하는 동안 몇몇 `SomeView`가 필요합니다. 
- That means that `CmdA` and `CmdB` might be good candidates to land in the same module!
- 이것은 `CmdA`, `CmdB`가 같은 모듈의 후보가 될 수 있음을 의미합니다.
- Let's put those commands (together with invariants and events) next to each other.
- 이제 이 commands(이벤트와 invariant와 함께) 같이 놓도록 합니다.

Doing so might segment our domain into very cohesive spots. Below we can find a proposed modularization. Remember that this is just a heuristic, you might end up with different setup. Proposed technique gives us a good chance to identify modules which are loosely coupled. This method is just a heuristic (not a strong rule) that can help us at finding independent modules. Also, if you think about it, proposed modules have linguistic boundaries. Credit card means something different for accounting and marketing, even though it’s the same word. In DDD terminology those are called *Bounded Contexts*. Those will be our deployment units. Also, this generalization must take into account if the effect should be immediate or eventual. If it can be eventually consistent, this heuristic is not that strong, even though there is a relationship.

이렇게 한다면 도메인을 매우 응집력 있게 설계할 수 있습니다. 이렇게 아래와 같이 모듈들을 나누어 볼수 있습니다. 이것은 단지 경험적 일 뿐이라는 것을 기억하십시오. 다른 설정으로 끝날 수도 있습니다. 이렇게 위해서 부터 나온 기술들로 인하여 우리는 결합도가 악햔 모듈들을 설계할 수 있습니다.이 같은 방법은 Heuristic(not a strong rule)하고 독립된 모듈들을 찾아내는데 도움을 줍니다. 또한 이것에 대해서 생각해보면 제안된 모듈들은 언어적인 경계를 갖지고 있습니다. 신용카드는 회계, 마케팅에서 같은 단어이지만 다른 의미로 사용이 됩니다. DDD에서는 Bounded Context라고 합니다. 이것은 우리에게 배포의 단위가 될 수 있습니다. 또한 이 일반화는 효과가 즉각적이거나 궁극적이어야하는지 고려헤야 합니다. 결과적으로 관계가 있음에도 불구하고 이 경험적 방법은 그렇게 강력하지 않을 수도 있습니다.

![modules](https://i.imgur.com/YJBU0WO.png)

The last step in DIVIDE part is to identify how modules communicate with each other. This is so-called context mapping. Here is a list of some integration strategies:

DIVIDE의 마지막 스탭은 이 모듈들이 어떻게 통신하는지(context-mapping)를 살펴보는 것입니다. 여기 몇 사례들이 있습니다.

- A module sends a query to another module - Statement module needs to ask Card Operations if there are any withdrawals. Because if not, it does not issue any statement.
- 모듈은 다른 모듈에게 쿼리를 보낸다. - Statement모듈은 card Operations에게 어떤 인출이 있었는지 질의해 봐야 합니다. 만약 인출이 없다면 그것은 Statement에 아무일도 있어 나지 않기 때문입니다.
- A module listens to events sent by another module - The direct consequence of *Money Repaid* event is *Statement Closed* event. That means that Statements shall subscribe to events thrown by Card Operations. That was missed at the beginning of Event Storming session. Context Mapping is actually a moment when we discover a lot of new information
- 모듈은 다른 모듈로 부터 보내진 이벤트를 수신합니다. - Money Repaid 이벤트의 직접적인 결과는 Statement Closed 이벤트입니다. 이것은 Statements가 Card Operations으로 부터 발행된 메시지를 구독한다는 것을 나타냅니다. 그것은 Event Storming 세션의 시작 부분에 놓친 것입니다. 컨텍스트 매핑은 실제로 많은 새로운 정보를 발견하는 과정입니다.
- A module fires a command to another module - no such example in our system.
- 모듈은 다른 모듈의 command를 실행시킵니다. 아직 이 예제는 없네요.

![contextmap](https://i.imgur.com/vBhouxJ.png)

### Implement

Having functionally decomposed software tremendously helps during its maintenance. Modular monolith is a good start, but the fact that it is a single deployment unit might cause problems. All of the modules must be deployed together. In some enterprises going with microservices may be a better option. Please refer to [this article](https://content.pivotal.io/blog/should-that-be-a-microservice-keep-these-six-factors-in-mind?utm_campaign=content-social&utm_medium=social-sprout&utm_source=twitter&utm_content=1516394228) by [Nate Shutta](https://twitter.com/ntschutta) in order to learn more about when this decision is right.

기능적으로 소프트웨어를 분리해 놓는 것은 유지보수에 많은 도움을 줍니다.  모노리딕 모듈러는 좋은 시작이 될 수 있지만 단일 배포라는 것 때문에 문제가 생길 수도 있습니다. 모든 모듈은 반드시 함께 배포되어져야 합니다. 몇몇의 회사에서는 MSA구조를 사용하는 것이 더 좋은 선택이 될 수 있습니다. 해당 링크는 이 같은 결정을 올바르게 내리기 위해서 배워야 하는 것들에 대해서 기술하고 있습니다.

Let’s assume that our example fits microservice architecture. Each module can be a separate Spring Boot application. We know the boundaries of the modules. Different architectural styles can be applied in each of them. The places which contain the most business logic should be implemented with careful attention. On the other hand, there are some modules which are clear and simple. How to find both?

우리의 예제가 MSA에 적합하다고 가정을 해봅시다. 각각의 모듈은 Spring-Boot 어플리케이션으로 분리될 수 있습니다. 우리는 모듈의 경계를 알고 있습니다. 각각 다른 스타일의 아키텍쳐를 적용할 수 있습니다. 대부분을 비즈니스 로직을 포함하는 부분은 주의 깊에 구현되야 하는 반면, 다른 일부 모듈은 명확하고 간단합니다. 어떻게 그 둘을 찾을 수 있을가요?

- Look for spots with a lot of yellow notes (invariants). This is where we have much logic in between a command and eventual event. System needs to process complex commands here. This is where we expect sudden changes and where we probably build competitive advantage. We want to apply special care here, thus for example apply the Domain-Driven Design techniques or hexagonal architecture.
- Look for spots that contain a few or zero yellow notes. Those are clear and easy to implement. There is almost nothing in between a command and an event, the system does not need to do anything complex here. The only job here is to interact with the database so we should be careful and try to avoid accidental complexity there.

That knowledge is a very important architectural driver that can make us decide to decouple *commands exposure* (e.g. REST resources) from *commands processing* (domain model with invariants). This architectural driver applied to Card Operations leads us to the following technology stack:





### Deploy

### Build Value

### REF
https://dzone.com/articles/spring-cloud-stream-with-kafka
